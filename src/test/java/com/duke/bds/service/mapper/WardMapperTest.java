package com.duke.bds.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WardMapperTest {

    private WardMapper wardMapper;

    @BeforeEach
    public void setUp() {
        wardMapper = new WardMapperImpl();
    }
}
