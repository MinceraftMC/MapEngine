package de.pianoman911.mapengine.api.clientside;

/**
 * Single frame of a map display.
 * The main purpose of this is to provide the entity IDs of the item frame and the interaction
 * entity for own packet handling and entity modification.
 * <p>
 * <b>Note: Frames only exist on the network level,
 * so the server doesn't know about this frame entity.</b>
 */
public interface IFrame extends IMap {

    /**
     * <b>Note: Frames only exist on the network level,
     * so the server doesn't know about this frame entity.</b>
     *
     * @return the entity id of the item frame
     */
    int frameEntityId();

    /**
     * <b>Note: Frames only exist on the network level,
     * so the server doesn't know about this frame entity.</b>
     *
     * @return the entity id of the interaction entity
     */
    int interactionEntityId();
}
