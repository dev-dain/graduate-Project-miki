package com.dayang.miki.service;

import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.TestColor;
import com.dayang.miki.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public List<TestColor> findByItem(Item item){
        return testRepository.findByItem(item);
    }
}
