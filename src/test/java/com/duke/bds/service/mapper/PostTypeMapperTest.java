package com.duke.bds.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostTypeMapperTest {

    private PostTypeMapper postTypeMapper;

    @BeforeEach
    public void setUp() {
        postTypeMapper = new PostTypeMapperImpl();
    }
}
