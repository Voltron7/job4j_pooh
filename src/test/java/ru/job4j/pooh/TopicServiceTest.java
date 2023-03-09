package ru.job4j.pooh;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class TopicServiceTest {

    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEqualTo("");
    }

    @Test
    public void whenTopicHasManyParamsForPublishers() {
        TopicService topicService = new TopicService();
        String paramForPublisher1 = "temperature=18";
        String paramForPublisher2 = "temperature=19";
        String paramForSubscriber1 = "client407";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher1)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher2)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        assertThat(result1.text()).isEqualTo(paramForPublisher1);
        assertThat(result2.text()).isEqualTo(paramForPublisher2);
    }
}