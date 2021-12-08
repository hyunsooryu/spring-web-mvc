package me.hyunsoo.handlermethod.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.Message;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class FormException extends RuntimeException{

    private final String message;

    private final Map<String, String> errorBox;

}
