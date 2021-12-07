package me.hyunsoo.handlermethod.controller;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @PathVariable
 *  요청 URI 패턴의 일부를 핸들러 메소드 아규먼트로 받는 방법입니다.
 *  타입 변환을 지원합니다. 기본 값이 반드시 있어야 하며, 옵셔널을 지원합니다.
 *
 * @MatrixVariable
 * 요청 URI 패턴에서 키/값 쌍의 데이터를 메소드 아규먼트로 받는 방법입니다.
 * 타입 변환을 지원하며, 기본 값이 반드시 있어야 합니다. Optional을 지원하며, 이 기능은 기본적으로 비활성화 되어있고, 활성화 하려면 설정이 필요합니다.
 * UrlPatternConfig 참조
 *
 */

@RequestMapping("/pattern")
@RestController
public class UriPatternController {

    @GetMapping(value = {"", "/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getEvent(@PathVariable Optional<Integer> id){

        return id.orElseGet(()->0);
    }
    /*
        id/q=10;r=11/name/q=andrew;r=monster

        @MatrixVariable -> MultiValueMap과 함께도 사용이 가능합니다.
    */
    @GetMapping(value = "/id/{id}/name/{name}")
    public Map<String, String> getInfoBox(@MatrixVariable(name = "q", pathVar = "id") String id,
                                          @MatrixVariable(name = "q", pathVar = "name") String name,
                                          @MatrixVariable MultiValueMap<String, String> multiValueMap){

        Map<String, String> infoBox = new HashMap<>();
        infoBox.put("id", id);
        infoBox.put("name", name);
        return infoBox;

    }



}
