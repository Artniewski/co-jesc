import Paragraph from "./Paragraph";

const Restaurant = ({name, html}) => {
    function formatMenuImage(html) {
        const parser = new DOMParser();
        const document = parser.parseFromString(html, "text/html");
        const reactions = document.querySelector('div[data-visualcompletion="ignore-dynamic"]');
        reactions?.remove();
        const img = document.querySelector('a img');
        img?.classList.add('menuImage')

        return img?.outerHTML ;
    }

    function formatMenuText(html) {
        const parser = new DOMParser();
        const document = parser.parseFromString(html, "text/html");
        const reactions = document.querySelector('div[data-visualcompletion="ignore-dynamic"]');
        reactions?.remove();
        const innerText = document.querySelector('div[dir="auto"]');
        innerText?.classList.add('menuText')

        return innerText?.outerHTML;

    }
    function formatText(text) {
        return  text?.replaceAll("\n", "<br>");
    }

    return (
        <div className='restaurant'>
            <Paragraph styleName='name' text={name} />
            <Paragraph styleName='menu' text={formatMenuText(html)} isHtml={true}/>
            <Paragraph styleName='menu' text={formatMenuImage(html)} isHtml={true}/>
        </div>
    );
}
export default Restaurant;