import StateCommand from "./StateCommand"

export default class NEW_ATTRIBUTE extends StateCommand {
    undo(attributes) {
        delete attributes[this.attributeId];
    }
    
    process(attributes, attribute) {
        attributes[this.attributeId] = attribute;
    }
}