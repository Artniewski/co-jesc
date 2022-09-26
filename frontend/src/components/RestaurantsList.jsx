import Restaurant from "./Restaurant";

const RestaurantsList = ({data}) =>{

    return (
        <div className="restaurantsList">
            <br></br>
            {data.length && data.map(restaurant => {
                console.log(restaurant.innerHtml)
                    return <Restaurant key={restaurant.facebookId} name={restaurant.restaurantName}
                                       menu={restaurant.innerHtml}/>
                }
            )}
        </div>
    );
}
export default RestaurantsList;