package com.duke.bds.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.duke.bds.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostTypeDTO.class);
        PostTypeDTO postTypeDTO1 = new PostTypeDTO();
        postTypeDTO1.setId(1L);
        PostTypeDTO postTypeDTO2 = new PostTypeDTO();
        assertThat(postTypeDTO1).isNotEqualTo(postTypeDTO2);
        postTypeDTO2.setId(postTypeDTO1.getId());
        assertThat(postTypeDTO1).isEqualTo(postTypeDTO2);
        postTypeDTO2.setId(2L);
        assertThat(postTypeDTO1).isNotEqualTo(postTypeDTO2);
        postTypeDTO1.setId(null);
        assertThat(postTypeDTO1).isNotEqualTo(postTypeDTO2);
    }
}
