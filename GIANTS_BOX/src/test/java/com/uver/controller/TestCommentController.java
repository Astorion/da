package com.uver.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.uver.cmn.Message;
import com.uver.service.CommentService;
import com.uver.vo.CommentVO;

//메소드 수행 순서:
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class) // 스프랭 테스트 컨텍스트 프레임워크의 JUnit기능 확장
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

public class TestCommentController {
	final Logger LOG = LoggerFactory.getLogger(TestCommentController.class);

	@Autowired
	WebApplicationContext webApplicationContext;

	List<CommentVO> commentVO;

	@Autowired
	CommentService commentService;

	// 브라우저 대신 Mock
	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		LOG.debug("=========================");
		LOG.debug("=setUp()=");
		commentVO = Arrays.asList(new CommentVO(1, 2, "10", "재밌어요", "", "ehgml", ""),
				new CommentVO(2, 4, "10", "재밌어요ㅋㅋ", "", "ehgml", ""));
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		LOG.debug("=mockMvc=" + mockMvc);
		assertThat(mockMvc, is(notNullValue()));
		LOG.debug("=========================");
	}

	@After
	public void tearDown() throws Exception {

	}

	// 오류남;
	@Test
	public int doInsert(CommentVO commentVO) throws Exception {
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/comment/doInsert.do")
				.param("commentSeq", commentVO.getCommentSeq() + "").param("seq", commentVO.getSeq() + "")
				.param("div", commentVO.getDiv()).param("content", commentVO.getContent())
				.param("regId", commentVO.getRegId());

		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.msgId", is("1")));
		String result = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		LOG.debug("===========================");
		LOG.debug("=result=" + result);
		LOG.debug("===========================");

		// json -> Message
		Gson gson = new Gson();
		Message outVO = gson.fromJson(result, Message.class);
		LOG.debug("===========================");
		LOG.debug("=Message=" + outVO);
		LOG.debug("===========================");

		return Integer.valueOf(outVO.getMsgId());
	}
}