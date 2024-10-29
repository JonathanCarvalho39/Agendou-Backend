package back.service.service;

import back.domain.dto.request.AvaliacaoRequestDTO;
import back.domain.dto.response.AvaliacaoResponseDTO;
import back.domain.mapper.AvaliacaoMapper;
import back.domain.model.Avaliacao;
import back.domain.repository.AvaliacaoRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvaliacaoService {
    private final String API_KEY = "f1ce01a6a5ceb2da73b862c3ca4a7d2b";

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public void submitFormRating(AvaliacaoRequestDTO avaliacaoRequest) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject body = new JSONObject();
        body.put("form_id", avaliacaoRequest.getJfId());
        body.put("answers", new JSONObject() {{
            put("control_star_rating", avaliacaoRequest.getEstrelas());
        }});

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.jotform.com/form/" + avaliacaoRequest.getJfId() + "/submissions?apiKey=" + API_KEY))
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao enviar a avaliação. Código: " + response.statusCode() + ", Mensagem: " + response.body());
        }
    }

    public List<AvaliacaoResponseDTO> getFormRatings() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.jotform.com/user/forms?apiKey=" + API_KEY))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONArray formsArray = new JSONObject(response.body()).getJSONArray("content");
        List<AvaliacaoResponseDTO> avaliacaoResponseDTOs = new ArrayList<>();

        for (int i = 0; i < formsArray.length(); i++) {
            JSONObject formJson = formsArray.getJSONObject(i);
            String formId = formJson.getString("id");

            int starRating = getStarRating(formId);
            Avaliacao avaliacao = new Avaliacao(null, formId, starRating);
            avaliacaoRepository.save(avaliacao);

            avaliacaoResponseDTOs.add(AvaliacaoMapper.toDTO(avaliacao));
        }

        return avaliacaoResponseDTOs;
    }

    private int getStarRating(String formId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.jotform.com/form/" + formId + "/submissions?apiKey=" + API_KEY))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONArray submissionsArray = new JSONObject(response.body()).getJSONArray("content");

        int totalStars = 0;
        int count = 0;

        for (int i = 0; i < submissionsArray.length(); i++) {
            JSONObject submissionJson = submissionsArray.getJSONObject(i);
            JSONObject answers = submissionJson.getJSONObject("answers");

            for (String key : answers.keySet()) {
                JSONObject answer = answers.getJSONObject(key);
                if ("control_star_rating".equals(answer.getString("type"))) {
                    int starRating = answer.getInt("answer");
                    totalStars += starRating;
                    count++;
                }
            }
        }

        return count > 0 ? totalStars / count : 0;
    }
}
