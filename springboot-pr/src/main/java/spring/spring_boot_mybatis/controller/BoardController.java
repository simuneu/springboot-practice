package spring.spring_boot_mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.spring_boot_mybatis.dto.BoardDTO;
import spring.spring_boot_mybatis.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping
    public List<BoardDTO> listBoards(){
        return boardService.getAllBoards();
    }

    @GetMapping("/{writer}")
    public BoardDTO getBoardByWriter(@PathVariable String writer){
        return boardService.getBoardByWriter(writer);
    }


    @PutMapping("/{id}")
    public BoardDTO updateBoard(@PathVariable int id, @RequestBody BoardDTO boardDTO){
        boardDTO.setId(id);
        boardService.updateBoard(boardDTO);
        return boardDTO;
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable int id){
        boardService.deleteBoard(id);
    }

    @PostMapping
    public BoardDTO createBoard(@RequestBody BoardDTO boardDTO){
        boardService.createBoard(boardDTO);
        return boardDTO;
    }
}
