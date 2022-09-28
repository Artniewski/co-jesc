import '../App.css';
import RestaurantsList from "../components/RestaurantsList";
import Title from "../components/Title";
import {useEffect, useState} from "react";

function App() {
    const [data, setData] = useState([{}]);
    const [restaurantsInfo, setRestaurantsInfo] = useState([]);

    async function getRestaurantsInfo() {
        const response = await fetch('/restaurants');
        const json = await response.json();
        setRestaurantsInfo(json)
    }
    async function getData(facebookId) {
        const response = await fetch(`${facebookId}/menu`);
        const json = await response.json();
        if(json.length > 0){
            handleAdd(json[0])
        }
    }
    function isFound(array, restaurant) {
        array.some(element => {
            return element.facebookId === restaurant.facebookId;
        });
        return false;
    }
    function handleAdd(restaurant) {
        setData(oldArray=> {
            return isFound(oldArray, restaurant) ? oldArray : [...oldArray, restaurant]
        })
    }

    useEffect( () => {
        getRestaurantsInfo()
    }, []);

    useEffect(() => {
        restaurantsInfo.map(restaurantInfo =>
            getData(restaurantInfo.facebookId)
        );
    }, [restaurantsInfo]);

    return (
        <div className="App">
            <Title title={"CO JEŚĆ"} />
            <RestaurantsList data={data} />
        </div>
    );
}

export default App;
