import Restaurant from "./Restaurant";

const RestaurantsList = ({data}) =>{

    return (
        <div className="restaurantsList">
            <br></br>
            {data.length && data.map(restaurant => {
                console.log(restaurant.content)
                    return <Restaurant key={restaurant.facebookId} name={restaurant.restaurantName}
                                       menu={restaurant.content}/>
                }
            )}
        </div>
    );
}
export default RestaurantsList;