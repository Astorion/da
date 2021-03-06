package com.uver.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.runners.Parameterized.UseParametersRunnerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.uver.cmn.Message;
import com.uver.cmn.Search;
import com.uver.cmn.StringUtil;
import com.uver.service.EventImgService;
import com.uver.service.EventService;
import com.uver.service.JoinService;
import com.uver.vo.EventVO;
import com.uver.vo.JoinVO;
import com.uver.vo.MemberVO;

@Controller("EventController")
public class EventController {

	// event_reg.jsp -> 이벤트 등록
	// event_list.jsp -> 이벤트 목록
	// event_view.jsp -> 이벤트 상세조회
	// event_mng.jsp -> 이벤트 수정/삭제/단건조회..!

	private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

	@Autowired
	EventService eventService;
	
	@Autowired
	JoinService joinService;

	@Autowired
	EventImgService eventImgService;

	@Autowired
	MessageSource messageSource;

	public EventController() {
	}

	@RequestMapping(value="myEvent.do", method=RequestMethod.GET)
	public ModelAndView goMyEvent(HttpSession session, EventVO event,
							@RequestParam String eventSeq) {

		LOG.debug("=============");
		LOG.debug("goMyEvnet()");
		LOG.debug("=============");
		
		String userId = null;
		ModelAndView mav = new ModelAndView();
		
		if(session.getAttribute("user") !=null) {
			MemberVO memberVO = (MemberVO) session.getAttribute("user");
			LOG.debug(memberVO.toString());
			userId = memberVO.getUserId();
		} 
		
		
		
		int eventSeqInt = Integer.parseInt(eventSeq);
		
		EventVO inVO = new EventVO();
		inVO.setEventSeq(eventSeqInt);
		
		//게시판 리스트를 다 부른다.
		
		
		
		// join 리스트 호출
		
		// model 
		
		// 뷰로 전달
		
		
		
		
		
		
		
		
		
		
		return mav;
			
	}
	
	//------------------------------------------------------------------
	//--- event_update 이동
	@RequestMapping(value="event_update.do", method=RequestMethod.GET)
	public ModelAndView goEventUpdate(@RequestParam("eventSeq") int eventSeq) {
		LOG.debug("-------------------");
		LOG.debug("goEventUpdate()");
		LOG.debug("-------------------");
		
		EventVO inVO = new EventVO();
		inVO.setEventSeq(eventSeq);
		
		EventVO outVO = this.eventService.doSelectOne(inVO);
		int imgSeq;
		
		try {
			imgSeq = this.eventImgService.doSelectThumbnail(eventSeq);
		} catch (Exception e) {
			imgSeq = 707;
			LOG.debug("exception: " + e.getMessage());
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("event", outVO);
		mav.addObject("imgSeq", imgSeq);
		mav.setViewName("event_update");
		return mav;
	}
	
	
	
	//--- event insert 하기
	@RequestMapping(value="event/doInsert.do",
			method = RequestMethod.POST,
			produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String doInsert(@RequestBody EventVO event, HttpSession session, Model model) {
		LOG.debug("=================");
		LOG.debug("=event=" + event);
		LOG.debug("=================");
		
		int seq = this.eventService.doInsertGetSeq(event);
		LOG.debug("=================");
		LOG.debug("=seq="+seq);
		LOG.debug("=================");
		
		Message message = new Message();
		
		if(session.getAttribute("user") !=null) {
			MemberVO memberVO = (MemberVO) session.getAttribute("user");
			LOG.debug(memberVO.toString());
			int memberSeq = memberVO.getSeq();
			int eventSeqInt = event.getEventSeq();
			
			JoinVO join = new JoinVO(eventSeqInt, memberSeq, 1);
			this.joinService.doInsert(join);
			// 메시지 처리
			message.setMsgId("eventSeq");
			
			if(seq > 0) {
				message.setMsgContents(eventSeqInt+"");
			} else {
				message.setMsgContents("등록 실패");
			}
		}

		Gson gson = new Gson();
		String json = gson.toJson(message);
		LOG.debug("==================");
		LOG.debug("=json=" + json);
		LOG.debug("==================");

		return json;
		
	}
	
	
	
	//---url 통일하려고 하단 doSelecOne 부분 조금 바꿔서 메서드 작성
	//---event_view.do?eventSeq=번호----------
	//--- 1개 이벤트 페이지로 이동
	@RequestMapping(value="event_view.do", method = RequestMethod.GET)
	public ModelAndView goEventView(HttpSession session,
			@RequestParam String eventSeq) {
		LOG.debug("-------------------");
		LOG.debug("eventView()");
		LOG.debug("-------------------");
		
		
		int memberSeq = 0;
		ModelAndView mav = new ModelAndView();
		
		if(session.getAttribute("user") !=null) {
			MemberVO memberVO = (MemberVO) session.getAttribute("user");
			LOG.debug(memberVO.toString());
			memberSeq = memberVO.getSeq();
		} 
		
		int eventSeqInt = Integer.parseInt(eventSeq);
		
		// event
		EventVO inVO = new EventVO();
		inVO.setEventSeq(eventSeqInt);
		EventVO outVO = this.eventService.doSelectOne(inVO);
		mav.addObject("eventVO", outVO);
		
		// member list
		JoinVO joinVO = new JoinVO();
		joinVO.setEventSeq(eventSeqInt);
		joinVO.setMemberSeq(memberSeq);
		
		int joinCheck = joinService.checkJoin(joinVO);
		List<JoinVO> joinList = joinService.doSelectList(joinVO);
		int joinCount = joinList.size();
		mav.addObject("joinCount", joinCount);
		mav.addObject("joinCheck", joinCheck);

		// movie 
		String movieId = outVO.getMovieInfo().substring(0, 1);
		String movieSeq = outVO.getMovieInfo().substring(1);
		mav.addObject("movieId", movieId);
		mav.addObject("movieSeq", movieSeq);
		
		// img
		int imgSeq = this.eventImgService.doSelectThumbnail(eventSeqInt);
		mav.addObject("imgSeq", imgSeq);
		
		
		mav.setViewName("event_view");
		return mav;
	}
	
	
	
	
	
	
	
	


	@RequestMapping(value = "event/doDelete.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String doDelete(EventVO event) {
		LOG.debug("=================");
		LOG.debug("=event=" + event);
		LOG.debug("=================");
		int flag = this.eventService.doDelete(event);

		// 메시지 처리
		Message message = new Message();
		message.setMsgId(flag + "");

		if (flag == 1) {
			message.setMsgContents("이벤트번호 [" + event.getEventSeq() + "]를 삭제합니다.");
		} else {
			message.setMsgContents(event.getEventSeq() + "의 삭제를 실패했습니다.");
		}

		Gson gson = new Gson();
		String json = gson.toJson(message);
		LOG.debug("==================");
		LOG.debug("=json=" + json);
		LOG.debug("==================");

		return json;
	}

	//---goEventView 메서드로 통합함-----------------------------
	@RequestMapping(value = "event/doUpdate.do",
			method = RequestMethod.POST,
			produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String doUpdate(@RequestBody EventVO event) {
		LOG.debug("==================");
		LOG.debug("=event=" + event);
		LOG.debug("==================");

		int flag = this.eventService.doUpdate(event);
		LOG.debug("==================");
		LOG.debug("=flag=" + flag);
		LOG.debug("==================");

		Message message = new Message();
		message.setMsgId(String.valueOf(flag));

		if (flag == 1) {
			message.setMsgContents("수정되었습니다.");
		} else {
			message.setMsgContents("수정 실패");
		}

		Gson gson = new Gson();
		String json = gson.toJson(message);
		LOG.debug("==================");
		LOG.debug("=json=" + json);
		LOG.debug("==================");
		return json;
	}
	//----------------------------------------------------
	


	@RequestMapping(value = "event/doSelectOne.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String doSelectOne(Model model , HttpServletRequest req) {
		LOG.debug("==================");
		  int memberSeq = 0;
		  HttpSession session = req.getSession();
		  if(session.getAttribute("user") !=null) {
			  MemberVO memberVO =(MemberVO)session.getAttribute("user");
			  LOG.debug(memberVO.toString());
			  memberSeq =  memberVO.getSeq();
		  }
	

		  String seletedSeq = (String) req.getParameter("seleted_seq");
		
		  EventVO inVO = new EventVO();
		  inVO.setEventSeq(Integer.parseInt(seletedSeq));
		  EventVO outVO = this.eventService.doSelectOne(inVO);
		  
		  
		  JoinVO joinVO = new JoinVO();
		  joinVO.setEventSeq(Integer.parseInt(seletedSeq));
		  joinVO.setMemberSeq(memberSeq);
		  int joinCheck = joinService.checkJoin(joinVO);
		  List<JoinVO> joinList = joinService.doSelectList(joinVO);
		  int joinCount = joinList.size();
		  model.addAttribute("joinCount", joinCount);
		  model.addAttribute("joinCheck", joinCheck);
		  
		  
		  String movieId  =outVO.getMovieInfo().substring(0,1);
		  String movieSeq  = outVO.getMovieInfo().substring(1);
		  model.addAttribute("movieId", movieId);
		  model.addAttribute("movieSeq", movieSeq);
		  model.addAttribute("eventVO", outVO);
		

		  return "event_view";
	}

	
	
	
	@RequestMapping(value = "event/doSelectList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String doSelectList(HttpServletRequest req) {
		LOG.debug("doSelectList");
		String searchWord = StringUtil.nvl((String) req.getParameter("searchWord"), "");
		LOG.debug(searchWord);
		String tmpStr =  StringUtil.nvl((String) req.getParameter("searchDate"), "");
		String searchDate = tmpStr.replaceAll("-", "");
		LOG.debug(searchDate);
		String genreStr = StringUtil.nvl((String) req.getParameter("genreStr"), "");
		LOG.debug(genreStr);
		String pageNum = StringUtil.nvl((String) req.getParameter("pageNum"), "1");
		LOG.debug("pagenum"+pageNum);
		Search search = new Search("10", searchWord, searchDate, 5, Integer.parseInt(pageNum));
		String [] genreArr = genreStr.split(",");
	
		for(int i = 0 ; i< genreArr.length ; i++) {
			
			search.addGenreList(genreArr[i]);
		}
		
		LOG.debug("search.toString()" + search.toString());
		
		
		// 페이지 num 기본값 처리
		if (search.getPageNum() == 0) {
			search.setPageNum(1);
		}

		// 페이지 사이즈 기본값 처리
		if (search.getPageSize() == 0) {
			search.setPageSize(10);
		}

		// 검색구분
		search.setSearchDiv(StringUtil.nvl(search.getSearchDiv(), ""));

		// 검색어
		search.setSearchWord(StringUtil.nvl(search.getSearchWord(), ""));
		LOG.debug("2==================");
		LOG.debug("=null처리 search=" + search);
		LOG.debug("==================");

		List<EventVO> list = this.eventService.doSelectList(search);
		
		// 이벤트 참여 인원 확인
		for(int i= 0 ; i <list.size() ; i++) {
			
			EventVO vo =  list.get(i);
			JoinVO jVO = new JoinVO();
			jVO.setEventSeq(vo.getEventSeq());
			
			List jList = joinService.currentJoinList(jVO);
			vo.setTotalCnt(jList.size());
			
		}
		
		
		if (list.size() > 0) {
			req.getSession().setAttribute("tot", list.get(0).getTotalCnt());
		}
		Gson gson = new Gson();

		String json = gson.toJson(list);
		LOG.debug("3==================");
		LOG.debug("=json=" + json);
		LOG.debug("==================");

		return json;
	}
	
	@RequestMapping(value = "event/doSelectList2.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String doSelectList2(Search search,int myDiv,HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute("user");
		
		
		if(myDiv == 1) {
			search.setSearchDiv("40");
			
			search.setSearchWord(member.getUserId());
		} else if(myDiv == 2){
			search.setSearchDiv("50");
			
			search.setSearchWord(String.valueOf(member.getSeq()));
		} else if(myDiv == 3) {
			search.setSearchDiv("60");
			
			search.setSearchWord("");
		} else {
		
		// 검색구분
		search.setSearchDiv(StringUtil.nvl(search.getSearchDiv(), ""));
	
		// 검색어
		search.setSearchWord(StringUtil.nvl(search.getSearchWord(), ""));
		}
		
		
		
		List<EventVO> list = this.eventService.doSelectList(search);
		
		Gson gson = new Gson();

		String json = gson.toJson(list);
		LOG.debug("3==================");
		LOG.debug("=json=" + json);
		LOG.debug("==================");

		return json;
	}

}
