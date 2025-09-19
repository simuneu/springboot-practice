package spring.spring_boot_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.spring_boot_jpa.dto.BoardDTO;
import spring.spring_boot_jpa.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    //전체 리스트
    @GetMapping
    @ResponseBody
    public List<BoardDTO> ListBoards(){
        return boardService.getAllBoards();
    }

    //상세 조화
    @GetMapping("/{id}")
    public BoardDTO getBoard(@PathVariable int id){
        return boardService.getBoard(id);
    }

    //등록
    @PostMapping
    public BoardDTO createBoard(@RequestBody BoardDTO boardDTO){
        boardService.createBoard(boardDTO);
        return boardDTO;
    }

    //수정
    @PutMapping("/{id}")
    public BoardDTO updateBoard(@PathVariable int id, @RequestBody BoardDTO boardDTO){
        boardDTO.setId(id);
        boardService.updateBoard(id, boardDTO);
        return boardDTO;
    }

    //삭제
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable int id){
        boardService.deleteBoard(id);
    }
}
