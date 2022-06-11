const endpoint = 'http://192.168.100.4:3000/api/v1';

class Repository {
  constructor(endpoint) {
    this.endpoint = endpoint;
  }

  fetchProducts(filters) {
    return fetch(`${this.endpoint}/products?size=${filters.size}`, {
      method: 'post',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        sortBy: filters.sortBy,
        priceLow: filters.price.low,
        priceHigh: filters.price.high,
      }),
    })
      .then((res) => {
        if (!res.ok) throw new Error(`Response status: ${res.status}`);
        return res.json();
      })
      .catch((res) => console.log(res));
  }

  fetchProduct(id) {
    return fetch(`${this.endpoint}/products/${id}`, {
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error(`Response status: ${res.status}`);
        return res.json();
      })
      .catch((res) => console.log(res));
  }

  createProduct(data) {
    return fetch(`${this.endpoint}/products/create`, {
      method: 'post',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    })
      .then((res) => {
        if (!res.ok) throw new Error(`Response status: ${res.status}`);
        return res.json();
      })
      .catch((res) => console.log(res));
  }
  
  getProductAttributes(productId) {
    return fetch(`${this.endpoint}/products/${productId}/attributes`, {
      method: 'get',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error(`Response status: ${res.status}`);
        return res.json();
      })
      .catch((res) => console.log(res));
  }

  addAttributes(productId, attributes) {
    return fetch(`${this.endpoint}/products/${productId}/add-attributes`, {
      method: 'post',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ attributes }),
    })
      .then((res) => {
        if (!res.ok) throw new Error(`Response status: ${res.status}`);
        return res.json();
      })
      .catch((res) => console.log(res));
  }

  connectAttributes(connections) {
    return fetch(`${this.endpoint}/attributes/connect-attributes`, {
      method: 'post',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ connections }),
    })
      .then((res) => {
        if (!res.ok) throw new Error(`Response status: ${res.status}`);
        return res.json();
      })
      .catch((res) => console.log(res));
  }

  disconnectAttributes(connections) {
    return fetch(`${this.endpoint}/attributes/disconnect-attributes`, {
      method: 'post',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ connections }),
    })
      .then((res) => {
        if (!res.ok) throw new Error(`Response status: ${res.status}`);
        return res.json();
      })
      .catch((res) => console.log(res));
  }

  deleteAttributeValue(valueId) {
    return fetch(`${this.endpoint}/attribute-value/${valueId}/delete`, {
      method: 'delete',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error(`Response status: ${res.status}`);
        return res.json();
      })
      .catch((res) => console.log(res));
  }

  createAttributeValue(attributeId, value) {
    return fetch(`${this.endpoint}/attributes/${attributeId}/add-value`, {
      method: 'post',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ value }),
    })
      .then((res) => {
        if (!res.ok) throw new Error(`Response status: ${res.status}`);
        return res.json();
      })
      .catch((res) => console.log(res));
  }

  sendTwentyOneFitChallengeRequest(data) {
    return fetch(`${this.endpoint}/challenges/21fit`, {
      method: 'post',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    })
      .then((res) => {
        if (!res.ok) throw new Error(`Response status: ${res.status}`);
        return res.json();
      })
      .catch((res) => console.log(res));
  }
}

const repository = new Repository(endpoint);
export default repository;
