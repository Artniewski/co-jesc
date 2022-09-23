import Paragraph from "./Paragraph";

const Restaurant = ({name, menu}) => {
    function formatMenu() {
        return menu?.replaceAll("\n", "<br>");
    }
    return (
        <div className='restaurant'>
            <Paragraph styleName='name' text={name} />
            <Paragraph styleName='menu' text={formatMenu()} isHtml={true}/>


        </div>
    );
}
export default Restaurant;