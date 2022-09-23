const express = require('express');
const scraper = require('./scraper.js')
const mockData = require('./example_result.json')

const app = express();
const PORT = process.env.PORT || 2137;

app.use(express.json());

app.get('/', (req, res) => {
    res.send('Welcome')
})


app.get("/mock/menu", async (req, res) => {
    res.send(mockData);
})


app.get("/:fbId/menu", async (req, res) => {
    let fbId = req.params.fbId;
    console.log(fbId)
    try {
        let posts = await scraper.getPosts(fbId,"text");
        res.send(posts);
    } catch (err) {
        console.error(err);
        return 500;
    }
})

app.get("/:fbId/html", async (req, res) => {
    let fbId = req.params.fbId;
    console.log(fbId)
    try {
        let posts = await scraper.getPosts(fbId,"html");
        res.send(posts);
    } catch (err) {
        console.error(err);
        return 500;
    }
})


app.listen(PORT, () => {
    console.log(`Server listening on port ${PORT}`);
})
