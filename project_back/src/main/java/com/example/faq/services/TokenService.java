package com.example.faq.services;

import com.example.faq.models.Token;
import com.example.faq.persistences.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    private TokenDao tokenDao;

    public Token findTokenByUserId(Integer id) {
        return tokenDao.findTokenByUserId(id);
    }

    public Integer updataToken(Token token) {
        return tokenDao.updataToken(token);
    }
}
