package br.com.voting.votingapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.voting.votingapp.dto.request.SessionRequestDTO;
import br.com.voting.votingapp.dto.request.TopicRequestDTO;
import br.com.voting.votingapp.dto.response.TopicResponseDTO;
import br.com.voting.votingapp.service.TopicService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest(classes = {TopicController.class})
@AutoConfigureMockMvc
@EnableWebMvc
class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicService topicService;

    @Test
    void shouldGetTopicInfoAndExpectNoErrors() throws Exception {
        // given
        TopicResponseDTO topicResponseDTO = TopicResponseDTO.builder()
                .id("634ffe6407bcc72fe63576f4")
                .name("Topic 1")
                .description("SimpleTopic")
                .noVotes(5L)
                .yesVotes(10L)
                .build();

        when(topicService.getTopicInfo(any())).thenReturn(topicResponseDTO);
        // when
        mockMvc.perform(get("/v1/topic/634ffe6407bcc72fe63576f4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":\"634ffe6407bcc72fe63576f4\"," +
                                                  "\"name\":\"Topic 1\"," +
                                                  "\"description\":\"SimpleTopic\"," +
                                                  "\"yesVotes\":10," +
                                                  "\"noVotes\":5," +
                                                  "\"openSession\":null}"));

    }

    @Test
    @Disabled
    void shouldNotGetTopicInfoAndExpectNotFoundException() throws Exception {
        //TODO
    }

    @Test
    void shouldCreateTopicAndExpectNoErrors() throws Exception {
        // when
        mockMvc.perform(post("/v1/topic")
                                .content("{\"name\":\"topic1\", \"description\":\"topic desc\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //then
        verify(topicService).create(any(TopicRequestDTO.class));
    }

    @Test
    void shouldCreateSessionAndExpectNoErrors() throws Exception {
        // given

        // when
        mockMvc.perform(
                        post("/v1/topic/634ffe6407bcc72fe63576f4/session")
                                .content("{\"description\":\"simple description\", \"endAt\":\"2022-10-18T13:55:12.208Z\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
        // then
        verify(topicService).createSession(any(String.class), any(SessionRequestDTO.class));
    }

    @Test
    @Disabled
    void shouldNotCreateSessionAndExpectNotFoundException() throws Exception {
        //TODO
    }

    @Test
    @Disabled
    void shouldNotCreateSessionAndExpectSessionAlreadyActiveException() throws Exception {
        //TODO
    }

    @Test
    @Disabled
    void shouldNotCreateSessionAndExpectEndDateBeforeCurrentDateException() throws Exception {
        //TODO
    }

    @Test
    void shouldRegisterVoteAndExpectNoErrors() throws Exception {

        // given
        // when
        mockMvc.perform(
                        post("/v1/topic/634ffe6407bcc72fe63576f4/vote")
                                .content("{\"userId\":\"12345678901\", \"vote\":\"YES\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
        // then
    }

    @Test
    @Disabled
    void shouldNotRegisterVoteAndExpectNotFoundException() throws Exception {
        //TODO
    }

    @Test
    @Disabled
    void shouldNotRegisterVoteAndExpectUserNotAbleToVoteException() throws Exception {
        //TODO
    }

    @Test
    @Disabled
    void shouldNotRegisterVoteAndExpectSessionEndedException() throws Exception {
        //TODO
    }

}