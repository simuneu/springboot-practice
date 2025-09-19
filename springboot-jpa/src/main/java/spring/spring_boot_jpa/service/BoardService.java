package spring.spring_boot_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.spring_boot_jpa.dto.BoardDTO;
import spring.spring_boot_jpa.entity.Board;
import spring.spring_boot_jpa.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public List<BoardDTO> getAllBoards(){
        List<Board> boards = boardRepository.findAll();
        List<BoardDTO> boardDTOs = new ArrayList<>();

        for(Board board: boards){
            BoardDTO boardDTO = convertToDto(board);
            boardDTOs.add(boardDTO);
        }
        return boardDTOs;
    }

    public BoardDTO getBoard(int id){
        Board board = boardRepository.findById(id).orElse(null);
        if(board == null){
            throw new RuntimeException("post not found");
        }
        return convertToDto(board);
    }

    public void createBoard(BoardDTO boardDTO){
        Board board = convertToEntity(boardDTO);

        boardRepository.save(board);
    }

    public void deleteBoard(int id){
        boardRepository.deleteById(id);
    }

    public void updateBoard(int id, BoardDTO boardDTO){
        Board board = convertToEntityWithId(id, boardDTO);
        boardRepository.save(board);
    }

    private BoardDTO convertToDto(Board board){
        return BoardDTO.builder()
                .no(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .id(board.getId())
                .build();
    }

    private Board convertToEntity(BoardDTO dto){
        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .id(dto.getId())
                .build();
    }

    private Board convertToEntityWithId(int id, BoardDTO dto){
        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .id(dto.getId())
                .build();
    }
}
