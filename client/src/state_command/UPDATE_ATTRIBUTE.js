import StateCommand from './StateCommand';

export default class UPDATE_ATTRIBUTE extends StateCommand {
  constructor(attributeId, oldValue) {
    super(attributeId);
    this.oldValue = { ...oldValue };
  }
  undo(attributes) {
    attributes[this.attributeId] = this.oldValue;
  }
  process(attributes) {
    //attributes[this.attributeId] = newValue;
    if (!attributes[this.attributeId].id.toString().startsWith('n')) {
      attributes[this.attributeId].operation = 'UPDATE';
    }
  }
}
