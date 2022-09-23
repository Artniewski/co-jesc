import '../App.css';
import RestaurantsList from "../components/RestaurantsList";
import Title from "../components/Title";
import {useEffect, useState} from "react";

function App() {
    const [data, setData] = useState([{}]);
    useEffect( () => {
        fetch("/all")
            .then(response => {
                return response.json();
            })
            .then((r) => {
                setData(r);
            })
            .catch(error => console.log('error:', error));
    }, []);
    return (
        <div className="App">
            <Title title={"CO JEŚĆ"} />
            <RestaurantsList data={data} />
        </div>
    );
}

export default App;
