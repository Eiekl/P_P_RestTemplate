import model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class Main {
    static RestTemplate restTemplate = new RestTemplate();
    static String url = "http://94.198.50.185:7081/api/users";
    static HttpHeaders headers = new HttpHeaders();

    public static void main(String[] args) {
        final String sessionId = getSessionId();

        headers.add("Cookie", sessionId);
        String result = saveUser() + editUser() + deleteUser(3);
        System.out.println(result);


//        ResponseEntity<List> users = restTemplate.getForEntity(url, List.class);
//        List list = users.getBody();
//        for (Object user: list) {
//            System.out.println(user);

        }


    public static String getSessionId() {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return String.join(";", Objects.requireNonNull(response.getHeaders().get("set-cookie")));

    }

    public static String saveUser() {
        User user = new User(3l, "James", "Brown", (byte) 18);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        return restTemplate.postForEntity(url, request, String.class).getBody();
    }

    public static String editUser() {
        User user = new User(3l, "Thomas", "Shelby", (byte) 18);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, request, String.class).getBody();

    }

    public static String deleteUser(long id) {
        HttpEntity<User> request = new HttpEntity<>(headers);
        return restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, request, String.class).getBody();


    }
}
