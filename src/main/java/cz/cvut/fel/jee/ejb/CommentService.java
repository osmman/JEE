package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.endpoints.websocket.CommentMessage;
import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.Video;
import java.util.List;

/**
 *
 * @author saljack
 */
public interface CommentService extends AbstractFacade<Comment>{

    Comment addCommentMessage(CommentMessage comment);

    List<Comment> findByVideo(Video video);
    
}
