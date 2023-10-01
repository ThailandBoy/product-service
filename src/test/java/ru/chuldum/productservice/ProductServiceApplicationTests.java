package ru.chuldum.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.chuldum.productservice.dto.ProductRequest;
import ru.chuldum.productservice.repository.ProductRepository;

import java.math.BigDecimal;

//Testcontainers - эта аннотация, дает знать JInit файлу, что мы собираемся использовать контейнеры для запуска теста

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc // Нужен для работы MockMvc
class ProductServiceApplicationTests {

    // Container - аннотация, даст JInit файлу понять что ниже это mongoDB контейнер, который будет исп. в тесте
    // Для срабатывания магии незабывайте писать аннотации!
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.24-focal");

    @Autowired
    private MockMvc mockMvc; // чтобы сделать запрос из нашего интеграционного теста нам нужен MockMvc

    @Autowired
    private ObjectMapper objectMapper; // он конвертирует POJO объект в JSON и обратно

    @Autowired
    private ProductRepository productRepository;

    // Этот метод принимает DynamicPropertyRegistry, и внутри него в принятый объект будут добавленны параметры
    // Для (Mongo database URI) перезапиши параметры host, port, username, and password
    // значениями из результата mongoDBContainer::getReplicaSetUrl.
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();

        // Здесь DTO (POJO) превращается в JSON но выгядит как String.
        String productRequestString = objectMapper.writeValueAsString(productRequest);

        // Короче это реквест билдер, здесь мы билдим запрос
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product") // Здесь мы делаем пост реквест, по адресу
                .contentType(MediaType.APPLICATION_JSON) // Здесь нам нужно предоставить тип тела запроса. Выбрали JSON
                .content(productRequestString)) // здесь контент для передачи в теле запросе, должен быть String (JSON)
                .andExpect(status().isCreated()); // здесь пишем то что ожидаем в ответ
        Assertions.assertTrue(productRepository.findAll().size() == 1);
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("iPhoneTestName")
                .description("IphoneTestDescr")
                .price(BigDecimal.valueOf(14000))
                .build();
    }

}
