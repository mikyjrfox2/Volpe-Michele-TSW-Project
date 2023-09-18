// Funzione per cercare i prodotti
function searchProducts() {
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("searchInput");
  filter = input.value.toUpperCase();
  table = document.querySelector("table");
  tr = table.getElementsByTagName("tr");

  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[1]; // Indice della colonna "Name" nella tabella
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}

// Lista dei prodotti disponibili (dovresti popolare questa lista con i dati dal server)
const availableProducts = [
  { code: "1", name: "Loopers Archetipe", description: "Complete Archetipe Box Looper, aggro/black, 68 cards", quantity: 20 },
  { code: "2", name: "Legion Archetipe", description: "Complete Archetipe Box Legion, mid/green, 68 cards", quantity: 17 },
  { code: "3", name: "Goldwarr Archetipe", description: "Complete Archetipe Box Goldwarr, mid/gray, 69 cards", quantity: 26 },

  { code: "5", name: "Abstractive Archetipe", description: "Complete Archetipe Box Abstractive, control/blue, 70 cards", quantity: 17 },
  { code: "6", name: "Power-ups", description: "Box of Power-up cards, mixed, 98 cards", quantity: 10 },
  
];

// Carrello (inizialmente vuoto)
let cartItems = [];

// Funzione per visualizzare i prodotti nella tabella
function displayProducts() {
  const table = document.querySelector("table");
  table.innerHTML = `
    <tr>
      <th>Code</th>
      <th>Name</th>
      <th>Description</th>
      <th>Remaining Pieces</th>
      <th>Add to Cart</th>
    </tr>
  `;

  availableProducts.forEach((product) => {
    const { code, name, description, quantity } = product;
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${code}</td>
      <td>${name}</td>
      <td>${description}</td>
      <td>${quantity}</td>
      <td><button onclick="addToCart('${code}')">Add to Cart</button></td>
    `;
    table.appendChild(row);
  });
}

// Funzione per aggiungere un prodotto al carrello
function addToCart(productCode) {
  const product = availableProducts.find((product) => product.code === productCode);

  if (product && product.quantity > 0) {
    cartItems.push(product);
    product.quantity--; // Riduci la quantità disponibile nel magazzino
    displayProducts();
    displayCartItems();
  }
}

// Funzione per visualizzare gli elementi del carrello
function displayCartItems() {
  const cartItemsList = document.getElementById("cartItems");
  cartItemsList.innerHTML = "";

  cartItems.forEach((item) => {
    const li = document.createElement("li");
    li.textContent = `${item.name} - Quantity: 1`; // Nel caso si voglia gestire la quantità diversa da 1, è necessario aggiungere questa informazione all'oggetto dell'elemento del carrello
    cartItemsList.appendChild(li);
  });
}

// Funzione per svuotare il carrello al checkout
function checkout() {
  cartItems = [];
  displayProducts();
  displayCartItems();
}

// Funzione per cercare i prodotti
function searchProducts() {
  // La stessa funzione di ricerca dal codice precedente
}

