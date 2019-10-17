package kr.co.ehr.boardAttr;


import org.springframework.test.web.servlet.request.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;



import kr.co.ehr.boardAttr.service.BoardAttr;
import kr.co.ehr.boardAttr.service.impl.BoardAttrDaoImpl;
import kr.co.ehr.code.service.Code;
import kr.co.ehr.code.service.impl.CodeDaoImpl;
import kr.co.ehr.user.service.Search;
import kr.co.ehr.user.service.User;
/*
POJO 객체를 테스트하는 것이라서 빠르다.
디펜더시가 상대적으로 적어서 테스트 코드 작성이 편하다.
단위 테스트하기 가장 이상적이다.
 */


@WebAppConfiguration  //테스트할 DI 컨테이너를 웹 애플리케이션 전용 DI 컨테이너로 처리
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//@Test NAME_ASCENDING으로 수행.
public class DaoBoardAttrWebTest {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private WebApplicationContext context;//테스트할 Application Context를 인젝션
	
	private MockMvc mockMvc;//테스트할 컨텍스트를 지정한 MockMvc를 생성
	
	//Test Data
	List<BoardAttr> list;
	
	@Autowired
	BoardAttrDaoImpl boardAttrDaoImpl;
	
	@Before
	public void setUp() {
		list  = Arrays.asList(
				 new BoardAttr("1","J01_ATTR_124제목","J01_ATTR_내용",0,"","admin","noDate")
				,new BoardAttr("2","J02_ATTR_124제목","J02_ATTR_내용",0,"","admin","noDate")
				,new BoardAttr("3","J03_ATTR_124제목","J03_ATTR_내용",0,"","admin","noDate")
				,new BoardAttr("4","J04_ATTR_124제목","J04_ATTR_내용",0,"","admin","noDate")
				,new BoardAttr("5","J05_ATTR_124제목","J05_ATTR_내용",0,"","admin","noDate")
				);		
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void get_searchTitleList() throws Exception {
		LOG.debug("======================================");
		LOG.debug("=01. 기존 데이터 삭제=");
		LOG.debug("======================================");
		Search search=new Search();
		search.setSearchWord("_124");
		List<BoardAttr> getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
			
		for(BoardAttr vo:getIdList) {
			do_delete(vo);
		}
		
		LOG.debug("======================================");
		LOG.debug("=02. 단건등록=");
		LOG.debug("======================================");
		for(BoardAttr vo:list) {
			do_save(vo);
		}
		
		//url,param,post/get
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.get("/board_attr/keywordSearch.do")
				.param("searchWord", "_124");
				
		ResultActions resultActions = mockMvc.perform(createMessage)
                .andExpect(status().isOk());	

		String result = resultActions.andDo(print())
		.andReturn()
		.getResponse().getContentAsString();

		LOG.debug("=====================================");
		LOG.debug("=result="+result);
		LOG.debug("=====================================");			
	}
	
	//U
	@Test
	@Ignore
	public void update() throws Exception {
		LOG.debug("======================================");
		LOG.debug("=01. 기존 데이터 삭제=");
		LOG.debug("======================================");
		Search search=new Search();
		search.setSearchWord("_124");
		List<BoardAttr> getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
			
		for(BoardAttr vo:getIdList) {
			do_delete(vo);
		}
		
		LOG.debug("======================================");
		LOG.debug("=02. 단건등록=");
		LOG.debug("======================================");
		for(BoardAttr vo:list) {
			do_save(vo);
		}		
		
		getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		//-------------------
		//등록데이터 수정
		//-------------------
		BoardAttr boardAttr=getIdList.get(0);
		boardAttr.setTitle(boardAttr.getTitle()+"_U");
		boardAttr.setContents(boardAttr.getContents()+"_U");
		boardAttr.setFileId(boardAttr.getFileId()+"_U");
		boardAttr.setRegId(boardAttr.getRegId()+"_U");	
		
		do_update(boardAttr);
		LOG.debug("======================================");
		LOG.debug("=03. 단건조회=");
		LOG.debug("======================================");		
		BoardAttr outVO = get_selectOne(boardAttr);
		
		LOG.debug("======================================");
		LOG.debug("=04. 비교=");
		LOG.debug("======================================");		
		checkData(outVO, getIdList.get(0));		
	}
	
	//CRD
	@Test
	@Ignore
	public void addAndGet() throws Exception {
		LOG.debug("======================================");
		LOG.debug("=01. 기존 데이터 삭제=");
		LOG.debug("======================================");
		Search search=new Search();
		search.setSearchWord("_124");
		List<BoardAttr> getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
			
		for(BoardAttr vo:getIdList) {
			do_delete(vo);
		}
		
		LOG.debug("======================================");
		LOG.debug("=02. 단건등록=");
		LOG.debug("======================================");
		for(BoardAttr vo:list) {
			do_save(vo);
		}
		
		
		LOG.debug("======================================");
		LOG.debug("=03. 조회=");
		LOG.debug("======================================");
		getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		
		
		for(BoardAttr vo:getIdList) {
			BoardAttr vsVO = get_selectOne(vo);
			checkData(vsVO,vo);
		}
	}
	
	private void checkData(BoardAttr org, BoardAttr vs) {
		assertThat(org.getTitle(), is(vs.getTitle()));
		assertThat(org.getContents(), is(vs.getContents()));
		assertThat(org.getRegId(), is(vs.getRegId()));
		assertThat(org.getReadCnt(), is(vs.getReadCnt()));
	}
	
	/**엑셀다운 
	 * @throws Exception */
	
	@Test
	@Ignore
	public void excelDown() throws Exception {
		//url,param,post/get: 
		//MockMvcRequestBuilders: 요청 데이터를 설정할 때 사용할 static 메서드
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.get("/board_attr/do_exceldown.do")
				.param("searchDiv", "10")
				.param("searchWord", "_124")
				.param("pageSize", "10")
				.param("pageNum", "1")
				.param("ext", "csv")
				;		
		/*
		perform()
		DispathcherServlet에 요청을 의뢰
		MockMvcRequestBuilders를 사용해 설정한 요청 데이터를 perform()의 인수로 전달
		get / post / fileUpload 와 같은 메서드 제공
		perform()에서 반환된 ResultActions() 호출
		실행 결과 검증
		 */
		//url call 결과 return
		ResultActions resultActions = mockMvc.perform(createMessage)
				                     .andExpect(status().isOk());	
		
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result="+result);
		LOG.debug("=====================================");			
	}
			
	
	@Test
	@Ignore
	public void get_retrieve() throws Exception {
		LOG.debug("======================================");
		LOG.debug("=01. 기존 데이터 삭제=");
		LOG.debug("======================================");
		Search search=new Search();
		search.setSearchWord("_124");
		List<BoardAttr> getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
			
		for(BoardAttr vo:getIdList) {
			do_delete(vo);
		}
		
		LOG.debug("======================================");
		LOG.debug("=02. 단건등록=");
		LOG.debug("======================================");
		for(BoardAttr vo:list) {
			do_save(vo);
		}
		
		//url,param,post/get
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.get("/board_attr/get_retrieve.do")
				.param("searchDiv", "10")
				.param("searchWord", "_124")
				.param("pageSize", "10")
				.param("pageNum", "1");
				
		//url call 결과 return
		MvcResult result = mockMvc.perform(createMessage)
                .andExpect(status().isOk())
                .andReturn()
                ;
			
		//result:return VO 객체로 됨.(결과 출력 않됨.)
		ModelAndView  modelAndView=result.getModelAndView();
		List<BoardAttr> list = (List<BoardAttr>) modelAndView.getModel().get("list");
		
		for(BoardAttr vo:list) {
			LOG.debug("=vo="+vo);
		}
		
		assertThat(5, is(list.size()));
		LOG.debug("=====================================");
		LOG.debug("=result="+result);
		LOG.debug("=====================================");			
	}
			
	private void do_update(BoardAttr inVO) throws Exception {
		//url,param
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.post("/board_attr/do_update.do")
				.param("boardId", inVO.getBoardId())
				.param("title", inVO.getTitle())
		        .param("contents", inVO.getContents())
		        .param("fileId", inVO.getFileId())
		        .param("regId", inVO.getRegId());		
		//url call 결과 return
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.msgId", is("1")));		
		
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result="+result);
		LOG.debug("=====================================");			
		
	}
	
	/**수정 */
	@Test
	@Ignore
	public void do_update() throws Exception {
		//url,param
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.post("/board_attr/do_update.do")
				.param("boardId", "1067")
				.param("title", "U_J_124_99_비오는 날 코딩.")
		        .param("contents", "U_J_124_비오는 날 코딩 내용.")
		        .param("fileId", "U_")
		        .param("regId", "U_admin_124");		
		//url call 결과 return
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.msgId", is("1")));		
		
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result="+result);
		LOG.debug("=====================================");			
		
	}
			
	private void do_save(BoardAttr vo) throws Exception {
		//url,param
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.post("/board_attr/do_save.do")
				.param("title", vo.getTitle())
		        .param("contents", vo.getContents())
		        .param("fileId", vo.getFileId())
		        .param("regId", vo.getRegId());
				
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.msgId", is("1")));		
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result="+result);
		LOG.debug("=====================================");				
	}
	
	/**저장 
	 * @throws Exception */
	@Test
	@Ignore
	public void do_save() throws Exception {
		//url,param
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.post("/board_attr/do_save.do")
				.param("title", "J_124_99_비오는 날 코딩.")
		        .param("contents", "J_124_비오는 날 코딩 내용.")
		        .param("fileId", "")
		        .param("regId", "admin_124");
				
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.msgId", is("1")));		
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result="+result);
		LOG.debug("=====================================");				
	}
			
	private BoardAttr get_selectOne(BoardAttr vo) throws Exception {

		//url,param
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.get("/board_attr/do_selectOne.do")
				.param("boardId", vo.getBoardId());	
		//url call 결과 return
		MvcResult result = mockMvc.perform(createMessage)
				                   .andExpect(status().isOk())
				                   .andReturn()
				                   ;
		
		//result:return VO 객체로 됨.(결과 출력 않됨.)
		ModelAndView  modelAndView=result.getModelAndView();
		
		BoardAttr outVO = (BoardAttr) modelAndView.getModel().get("vo");
		LOG.debug("=====================================");
		
		LOG.debug("=outVO="+outVO);
		LOG.debug("=====================================");		
		
		return outVO;
	}
	
	
	@Test
	@Ignore
	public void get_selectOne() throws Exception {
		//url,param
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.get("/board_attr/do_selectOne.do")
				.param("boardId", "1053");	
		//url call 결과 return
		ResultActions resultActions = mockMvc.perform(createMessage)
				                     .andExpect(status().isOk());
		
		//result:return VO 객체로 됨.(결과 출력 않됨.)
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result="+result);
		LOG.debug("=====================================");		
	}
	
			
	/**
	 * Data삭제:
	 * @param user
	 * @throws Exception 
	 */
	private void do_delete(BoardAttr user) throws Exception {
		//url,param
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.post("/board_attr/do_delete.do")
				.param("boardId", user.getBoardId());
				 
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result="+result);
		LOG.debug("=====================================");		
	}
	/**삭제 
	 * @throws Exception */
	@Test
	@Ignore
	public void do_delete() throws Exception {
		//url,param
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.post("/board_attr/do_delete.do")
				.param("boardId", "1052");
				 
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.msgId", is("1")));
		
		
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result="+result);
		LOG.debug("=====================================");
				        
		
	}
	
	
	
	//junit의 테스트 메소드 작성방법
	//@Test public void 메소드이름(파라메터 사용금지){ }
	@Test
	public void getBean() {
		LOG.debug("====================");
		LOG.debug("=context="+context);
		LOG.debug("=mockMvc="+mockMvc);
		LOG.debug("====================");
		assertThat(context,  is(notNullValue()));
		assertThat(mockMvc,  is(notNullValue()));
	}
	
	
	@After
	public void tearDown() {
	
	}
	
	
	
}


