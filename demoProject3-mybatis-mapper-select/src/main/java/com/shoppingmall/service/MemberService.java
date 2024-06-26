package com.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingmall.dto.Member;
import com.shoppingmall.mapper.MemberMapper;

@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	
	public void insertMember(Member member) {
		memberMapper.insertMember(member);
	}
	
	public List<Member> getAllMember() {
		return memberMapper.getAllMember();
	}
}
