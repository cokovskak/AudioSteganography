package com.example.proba6.Controller;

import com.example.proba6.Exception.Golemina;
import com.example.proba6.Service.MailSend;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static com.example.proba6.config.EncoderDecoder.*;

@Controller
public class Logic {
    public static String generateRandomString() {
        int length = 4;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }


    @Autowired
    private MailSend pr;

    @GetMapping("/get_decode")
    public String Get_decode () {
        return "Decode"; }

    @GetMapping("/get_encode")
    public String Get_encode () {
        return "Encode"; }
    @GetMapping("/home")
    public String a()
    {
        return "EncodeDecode";
    }
    @PostMapping ("/encode")
    public String triggerMail(String Email,String Message,String audio, Model model) throws MessagingException {
        String random = generateRandomString();
        Integer messageSize = Message.length();
        String outputFilePath = "/home/kristina/Documents/verzija3_1/Neta/src/main/resources/static/Output/Song_" + random + messageSize +  ".wav";
        int choice = Integer.parseInt(audio);
        String inputFilePath;
        switch (choice) {
            case 1:

                inputFilePath = "/home/kristina/Documents/verzija3_1/Neta/src/main/resources/static/Audio/O1.wav";
                break;
            case 2:

                inputFilePath = "/home/kristina/Documents/verzija3_1/Neta/src/main/resources/static/Audio/O2.wav";
                break;
            case 3:

                inputFilePath = "/home/kristina/Documents/verzija3_1/Neta/src/main/resources/static/Audio/O3.wav";
                break;
            case 4:

                inputFilePath = "/home/kristina/Documents/verzija3_1/Neta/src/main/resources/static/Audio/O4.wav";
                break;
            case 5:

                inputFilePath = "/home/kristina/Documents/verzija3_1/Neta/src/main/resources/static/Audio/O5.wav";
                break;
            default:
                inputFilePath = "/home/kristina/Documents/verzija3_1/Neta/src/main/resources/static/Audio/O1.wav";

        }
        // za proverka
       // String poraka=generateLargeStringMessage();
        try {
            encodeAudioData(inputFilePath, outputFilePath, Message);
        } catch (Golemina e) {
            model.addAttribute("end", e.getMessage());
            return "Success";
        }

        pr.mailWithAttachment(Email,
                "You received a song",
                "Song " ,
                outputFilePath
        );
        System.out.println(messageSize);
        model.addAttribute("end", "You have successfully sent the hidden message.");
        return "Success";
    }

}