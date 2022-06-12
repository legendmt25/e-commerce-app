import StateCommand from './StateCommand';

export default class DELETE_ATTRIBUTE extends StateCommand {
  constructor(attributeId, value) {
    super(attributeId);
    this.value = { ...value };
  }
  undo(attributes) {
    attributes[this.attributeId] = this.value;
  }

  process(attributes) {
    if (!attributes[this.attributeId].id.toString().startsWith('n')) {
      attributes[this.attributeId].operation = 'DELETE';
      return;
    }
    delete attributes[this.attributeId];
  }
}
