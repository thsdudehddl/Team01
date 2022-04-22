package com.springstudy.bbs.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springstudy.bbs.domain.Board;

@Repository
public class BoardDaoImpl implements BoardDao {
	
	private final String NAME_SPACE = "com.springstudy.bbs.mapper.BoardMapper";
	
	private SqlSessionTemplate sqlSession;
		
	@Autowired
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	// com.springstudy.bbs.mapper.BoardMapper.boardList
	@Override
	public List<Board> boardList() {		 
		return sqlSession.selectList(NAME_SPACE + ".boardList");
	}

	@Override
	public Board getBoard(int no) {
		
		return sqlSession.selectOne(NAME_SPACE + ".boardDetail", no);
	}

	@Override
	public void insertBoard(Board board) {
		sqlSession.insert(NAME_SPACE + ".insertBoard", board);

	}

	@Override
	public boolean isPassCheck(int no, String pass) {
		boolean result = false;
		
		String dbPass = sqlSession.selectOne(NAME_SPACE + ".isPassCheck", no);
		
		if(dbPass.equals(pass)) {
			result = true;
		}
		
		return result;
	}

	@Override
	public void updateBoard(Board board) {
		sqlSession.update(NAME_SPACE + ".updateBoard", board);

	}

	@Override
	public void deleteBoard(int no) {
		sqlSession.delete(NAME_SPACE + ".deleteBoard", no);

	}

}
