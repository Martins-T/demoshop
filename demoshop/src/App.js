import { useState, useEffect } from "react";

const API_URL = "https://demoshop-qncl.onrender.com/products";

function App() {
    const [products, setProducts] = useState([]);

    const fetchProducts = async () => {
        try {
            const response = await fetch(API_URL);
            const data = await response.json();
            setProducts(data);
        } catch (error) {
            console.error("Kļūda ielādējot produktus:", error);
        }
    };

    const updateStock = async (id) => {
        try {
            const response = await fetch(`${API_URL}/${id}/stock`);
            const stock = await response.json();
            setProducts(products.map(p => p.id === id ? { ...p, stock } : p));
        } catch (error) {
            console.error("Kļūda atjaunojot atlikumu:", error);
        }
    };

    const deleteAllProducts = async () => {
        try {
            await fetch(API_URL, { method: "DELETE" });
            setProducts([]);
        } catch (error) {
            console.error("Kļūda dzēšot produktus:", error);
        }
    };

    const addProduct = async () => {
        const newProduct = {
            name: "Jauns Produkts",
            description: "Apraksts",
            price: Math.floor(Math.random() * 1000),
            stock: Math.floor(Math.random() * 100)
        };

        try {
            const response = await fetch(API_URL, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(newProduct)
            });

            const addedProduct = await response.json();
            setProducts([...products, addedProduct]);
        } catch (error) {
            console.error("Kļūda pievienojot produktu:", error);
        }
    };

    const deleteProduct = async (id) => {
        try {
            await fetch(`${API_URL}/${id}`, { method: "DELETE" });
            setProducts(products.filter(p => p.id !== id));
        } catch (error) {
            console.error("Kļūda dzēšot produktu:", error);
        }
    };

    useEffect(() => {
        fetchProducts();
    }, []);

    return (
        <div>
            <h1>Internetveikals</h1>
            <div style={{
                marginBottom: "20px",
                display: "flex",
                flexDirection: "column",
                gap: "10px",
                maxWidth: "250px"
            }}>
                <button onClick={fetchProducts}>
                    Ielādēt produktus
                </button>

                <button onClick={addProduct}>
                    Pievienot nejaušu produktu
                </button>

                <button style={{ color: "red" }} onClick={deleteAllProducts}>
                    Dzēst visus produktus
                </button>
            </div>
            <ul>
                {products.map(product => (
                    <li key={product.id} style={{marginBottom: "10px"}}>
                        <strong>{product.name}</strong> – {product.price}€ <br/>
                        <i>{product.description}</i> <br/>
                        ID: {product.id} <br/>
                        Atlikums: {product.stock}

                        <button
                            style={{marginLeft: "10px"}}
                            onClick={() => updateStock(product.id)}
                        >
                            Atjaunot atlikumu
                        </button> <br/>

                        <button
                            style={{marginTop: "10px", color: "red"}}
                            onClick={() => deleteProduct(product.id)}
                        >
                            Dzēst produktu
                        </button>
                    </li>

                ))}
            </ul>
        </div>
    );

}

export default App;
