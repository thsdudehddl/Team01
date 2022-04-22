package com.springstudy.bbs.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springstudy.bbs.domain.Board;
import com.springstudy.bbs.service.BoardService;

@Controller
public class BoardController {
	
	//  의존하는 객체를 DI 받을 수 있도록 - Property
	private BoardService boardService;

	@Autowired
	public void setBoardSerive(BoardService boardService) {
		this.boardService = boardService;
	}
	
	
	// http://localhost:8080/boardList
	@RequestMapping({"/boardList", "/list", "/"})
	public String boardList(Model model) {
		
		List<Board> bList = boardService.boardList();
		
		model.addAttribute("bList", bList);
		
		// 뷰 리졸버에 설정한 prefix + boardList + suffix
		return "boardList";
	}
	
	@RequestMapping("/boardDetail")
	public String boardDetail(Model model, int no) {
		
		Board board = boardService.getBoard(no);
		model.addAttribute("board", board);
		
		return "boardDetail";
	}
	
	@RequestMapping(value="/writeProcess", method=RequestMethod.POST)
	public String writeProcess(Board board) {
		
		boardService.insertBoard(board);
		
		return "redirect:boardList";
	}
	
	@RequestMapping(value="/update")
	public String updateBoard(Model model, HttpServletResponse response,
				PrintWriter out, int no, String pass) {
		
		boolean result = boardService.isPassCheck(no, pass);
		
		if(! result) {
			
			response.setContentType("text/html; charset=utf-8");
			out.println("<script>");
			out.println("alert('비밀번호가 맞지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
			return null;
		}
		
		Board board = boardService.getBoard(no);
		
		model.addAttribute("board", board);
		
		return "updateForm";
	}
	
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String updateBoard(HttpServletResponse response,
				PrintWriter out, Board board) {
		
		boolean result = boardService.isPassCheck(board.getNo(), board.getPass());
		
		if(! result) {
			
			response.setContentType("text/html; charset=utf-8");
			out.println("<script>");
			out.println("alert('비밀번호가 맞지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
			return null;
		}
		
		boardService.updateBoard(board);
		
		return "redirect:boardList";
	}
	
	
	
	@RequestMapping({"/delete", "deleteBoard"})
	public String deleteBoard(HttpServletResponse response,
				PrintWriter out, int no, String pass) {
		
		boolean result = boardService.isPassCheck(no, pass);
		
		if(! result) {
			
			response.setContentType("text/html; charset=utf-8");
			out.println("<script>");
			out.println("alert('비밀번호가 맞지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
			return null;
		}
		
		boardService.deleteBoard(no);
		
		return "redirect:boardList";
	}
	@RequestMapping("getGit")
	public String getGit() {
		return "getGit";
	}
	
}
