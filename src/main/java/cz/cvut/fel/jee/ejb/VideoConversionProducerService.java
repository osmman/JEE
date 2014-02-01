package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.Video;

/**
 *
 * @author saljack
 */
public interface VideoConversionProducerService {
    public void sendMessage(Video video, String input, String output);
}
