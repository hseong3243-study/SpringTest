package com.example.restdocs.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.restdocs.enumtype.EnumType;
import com.example.restdocs.enumtype.Provider;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(EnumDocsController.class)
@AutoConfigureRestDocs
public class EnumDocumentationTest {

    private static final String TITLE = "title";  // enum의 문서화에 사용되는 키 값

    @Autowired
    MockMvc mockMvc;

    @Test
    void provider() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/provider")
            .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
            .andDo(document("Provider",
                customResponseFields("custom-response",  // 스니펫 파일의 이름
                    attributes(key(TITLE).value("프로바이더")),  // 문서화한 enum의 타이틀
                    null,  // API 응답을 감싸서 전달하는 경우 beneathPath("data").withSubsectionId("providers") 추가할 것
                    enumConvertFieldDescriptor(Provider.values()))));
    }

    private FieldDescriptor[] enumConvertFieldDescriptor(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
            .map(enumType -> fieldWithPath(enumType.getName()).description(enumType.getValue()))
            .toArray(FieldDescriptor[]::new);
    }

    public static CustomResponseFieldsSnippet customResponseFields(String type,
        Map<String, Object> attributes,
        PayloadSubsectionExtractor<?> subsectionExtractor,
        FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, Arrays.asList(descriptors), attributes,
            true, subsectionExtractor);
    }
}
