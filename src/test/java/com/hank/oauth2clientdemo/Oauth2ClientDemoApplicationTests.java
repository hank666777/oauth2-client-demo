package com.hank.oauth2clientdemo;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Log4j2
@ActiveProfiles
@SpringBootTest
class Oauth2ClientDemoApplicationTests {

    @Autowired
    private Gson gson;


}
