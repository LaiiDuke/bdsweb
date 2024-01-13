package com.duke.bds.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.duke.bds.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostType.class);
        PostType postType1 = new PostType();
        postType1.setId(1L);
        PostType postType2 = new PostType();
        postType2.setId(postType1.getId());
        assertThat(postType1).isEqualTo(postType2);
        postType2.setId(2L);
        assertThat(postType1).isNotEqualTo(postType2);
        postType1.setId(null);
        assertThat(postType1).isNotEqualTo(postType2);
    }
}
