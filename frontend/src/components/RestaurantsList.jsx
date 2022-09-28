import Restaurant from "./Restaurant";

const RestaurantsList = ({data}) =>{

    return (
        <div className="restaurantsList">
            <br></br>
            {data.length && data.map(restaurant => {
                    return <Restaurant key={restaurant.facebookId} name={restaurant.restaurantName}
                                       html={restaurant.innerHtml} text={restaurant.innerText}/>
                }
            )}
        </div>
    );
}
export default RestaurantsList;