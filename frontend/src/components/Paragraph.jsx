const Paragraph = ({text, styleName, isHtml = false}) => {
    return (
        <div className={styleName}>{isHtml ?  <div dangerouslySetInnerHTML={{ __html: text }}>
        </div> :text}</div>
    );
}

export default Paragraph;