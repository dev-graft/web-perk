package devgraft.crypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("crypto")
@Controller
public class CryptoController {
    @GetMapping
    public String home() {
        return "crypto-md5";
    }
}
