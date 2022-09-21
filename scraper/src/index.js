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
    let posts = await scraper.getPosts(fbId);
    res.send(posts);
})

app.listen(PORT, () => {
    console.log(`Server listening on port ${PORT}`);
})
