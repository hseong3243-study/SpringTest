package com.example.restdocs;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(HelloController.class)
@AutoConfigureRestDocs
class HelloControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void helloWorld() throws Exception {
        //given
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/hello"));

        //then
        resultActions.andDo(print())
            .andExpect(status().isOk())
            .andDo(document("Hello, World!",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("data").type(STRING).description("데이터")
                )));
    }

    @Test
    void helloOptional() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/optional")
            .queryParam("required", "필수값"));

        //then
        resultActions.andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("Hello, Optional!",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                queryParameters(
                    parameterWithName("required").description("필수값"),
                    parameterWithName("value").description("문자열").optional()
                )));
    }

    @Test
    void helloOptionalBody() throws Exception {
        //given
        OptionalBody body = new OptionalBody("required", null);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/optional-body")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)));

        //then
        resultActions.andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("Hello, OptionalBody!",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("required").type(STRING).description("필수값"),
                    fieldWithPath("optional").type(STRING).description("옵셔널").optional()
                )));
    }
}
