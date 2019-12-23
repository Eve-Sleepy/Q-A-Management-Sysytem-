package com.example.faq.persistences;


import com.example.faq.models.Token;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDao {
    Integer addToken(Token token);

    Integer updataToken(Token token);

    Token findTokenByUserId(int userid);
}

