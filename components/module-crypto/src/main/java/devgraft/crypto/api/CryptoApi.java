package devgraft.crypto.api;

import devgraft.crypto.MD5;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/crypto")
@RestController
public class CryptoApi {

    @GetMapping("md5")
    public String generateToMD5(@RequestParam(name = "text") final String text) {
        return MD5.encrypt(text);
    }
}
