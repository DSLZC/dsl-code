package com.dslcode.apps.controller.core;

import com.dslcode.core.random.RandomCode;
import com.dslcode.core.util.ImageUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dongsilin on 2016/11/3.
 */
@RestController
@RequestMapping("/randomcode")
public class RandomCodeController {

    @PostMapping("/intCode")
    public String getCode(Integer len){
        return RandomCode.getNumCode(len);
    }

    @RequestMapping("/captcha")
    public void captcha(HttpServletResponse response, Integer len){
        try {
            ImageUtil.createCaptchaImg(RandomCode.getNumLetterLowerCode(len), response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
