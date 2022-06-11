export class Graph {
  constructor(n, nodeValues = []) {
    this.n = n;
    this.adj = {};
    this.nodeValues = nodeValues;
  }

  static createGraphForProduct(product) {
    const values = product.attributes.flatMap((attr) => {
      attr.values.forEach((val) => (val.attr_id = attr.id));
      return attr.values;
    });
    return Graph.createGraph(values.length, product.connections, values);
  }

  static createGraph(n, connections, values) {
    const graph = new Graph(n);
    graph.nodeValues = {};
    values.forEach((value) => {
      value.show = true;
      graph.nodeValues[value.id] = value;
    });
    connections.forEach((connection) => {
      const [x, y] = connection.split(',');
      graph.addEdge(x, y);
    });
    return graph;
  }

  bfsR(start, end, visited, visitedAttrs, result) {
    if (visited[start]) return;
    visited[start] = true;
    for (const x of this.adj[start]) {
      const nodeValue = this.getNodeValue(x);
      if (this.hasEdge(x, end)) {
        result.add(+x);
      } else if (!visitedAttrs[nodeValue.attr_id]) {
        result.add(+x);
        visitedAttrs[nodeValue.attr_id] = true;
      } else if (nodeValue.attr_id == this.getNodeValue(end).attr_id) {
        result.add(+x);
      }
      this.bfsR(x, end, visited, visitedAttrs, result);
    }
  }

  bfs(start, end) {
    const visitedAttrs = Object.values(this.nodeValues).reduce(
      (x, y) => ({
        ...x,
        [y.attr_id]: false,
      }),
      {}
    );
    const visited = Object.keys(this.adj).reduce(
      (x, y) => ({ ...x, [y]: false }),
      {}
    );
    const result = new Set([start]);
    this.bfsR(start, end, visited, visitedAttrs, result);
    return result;
  }

  hasEdge(x, y) {
    return this.adj[x].has(y);
  }

  addEdge(x, y) {
    if (!(x in this.adj)) this.adj[x] = new Set();
    if (!(y in this.adj)) this.adj[y] = new Set();
    this.adj[x].add(y);
    this.adj[y].add(x);
  }

  getNodeValue(x) {
    return this.nodeValues[x];
  }
  getNodeValues() {
    return this.nodeValues;
  }
  getEdges() {
    return this.adj.flatMap((x, index) => [...x].map((y) => `${index},${y}`));
  }
}
