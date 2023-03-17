package my.example.restdocs.utils;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.operation.preprocess.UriModifyingOperationPreprocessor;
import org.springframework.restdocs.snippet.Attributes;

import java.util.List;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.snippet.Attributes.key;

public interface ApiDocumentUtils {

    static UriModifyingOperationPreprocessor uriModifyingOperationPreprocessor() {
        return modifyUris()
                .scheme("https")
                .host("t-www.campingtalk.me")
                .removePort();
    }

    static List<String> descriptionsForNameProperty(Class clazz, String property) {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(clazz);
        List<String> nameDescription = constraintDescriptions.descriptionsForProperty(property);
        //System.out.println("nameDescription = " + nameDescription);

        return nameDescription;
    }


    /**
     * 날짜 포맷 Attribute
     * @return
     */
    static Attributes.Attribute getDateFormat() { // (2)
        return key("format").value("yyyyMMdd");
    }

    static Attributes.Attribute getPhoneFormat() { // (2)
        return key("format").value("10~11자리 숫자형 문자열(-제외)");
    }
}
