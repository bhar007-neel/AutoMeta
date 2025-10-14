<div align="center" style="font-family: 'Segoe UI', sans-serif; color: #f0f0f0; background: linear-gradient(120deg, #007BFF, #6610f2, #6f42c1); padding: 30px; border-radius: 12px; box-shadow: 0 4px 30px rgba(0,0,0,0.3);">

  <h1 style="font-size: 48px; letter-spacing: 2px; color: #fff;">🚀 AutoMeta</h1>
  <h3 style="color: #dcdcdc;">An Intelligent YouTube Metadata Analyzer & Tag Generator</h3>
  <p style="margin-top: 10px;">
  <a href="https://autometa-a2pr.onrender.com/" 
     target="_blank" 
     style="color: #00bfff; text-decoration: none; font-weight: bold;">
     🔗 Live Demo
  </a>
</p>

  <img src="https://skillicons.dev/icons?i=java,spring,html,css,js,maven,docker,github" alt="Tech Stack" width="400" style="margin-top: 20px;">

  <p style="font-size: 18px; color: #fff; max-width: 700px; margin-top: 20px;">
    <b>AutoMeta</b> is a full-stack <b>Spring Boot</b> web application that integrates with the 
    <b>YouTube Data API</b> to search, analyze, and extract video metadata such as titles, tags, 
    and thumbnails — helping content creators discover related content and optimize SEO tags 
    effortlessly. Deployed on <b>Render</b> using a custom <b>Dockerfile</b>.
  </p>
</div>

---

<h2 style="color:#007bff;">🧩 Core Features</h2>
<ul style="font-size:16px; line-height:1.7;">
  <li>🔍 <b>YouTube Video Search</b> – Fetches metadata (title, channel, thumbnail, and tags) via YouTube Data API.</li>
  <li>🧠 <b>Smart Tag Extraction</b> – Automatically generates recommended keywords to improve video SEO.</li>
  <li>⚡ <b>Asynchronous WebClient</b> – Built using Spring's non-blocking reactive <code>WebClient</code>.</li>
  <li>🗂️ <b>Modular MVC Architecture</b> – Organized in Controller, Service, and Model layers for scalability.</li>
  <li>🌐 <b>API Endpoints</b> – RESTful design to fetch video details, thumbnails, and related results.</li>
  <li>🐳 <b>Dockerized Deployment</b> – Configured for fast build and lightweight deployment using Docker multi-stage build.</li>
  <li>☁️ <b>Deployed on Render</b> – Automatically builds from GitHub with environment variable integration.</li>
</ul>

---

<h2 style="color:#007bff;">🧠 Tech Stack</h2>
<table style="width:100%; font-size:16px; border-collapse: collapse;">
  <tr>
    <td style="padding: 8px;">💻 <b>Backend</b></td>
    <td>Java 17, Spring Boot 3.5.6, Maven, Lombok</td>
  </tr>
  <tr>
    <td style="padding: 8px;">🌐 <b>Frontend</b></td>
    <td>HTML5, CSS3, JavaScript, Thymeleaf (optional)</td>
  </tr>
  <tr>
    <td style="padding: 8px;">☁️ <b>API</b></td>
    <td>YouTube Data API (v3)</td>
  </tr>
  <tr>
    <td style="padding: 8px;">🐳 <b>DevOps</b></td>
    <td>Docker, Render, GitHub Actions (optional)</td>
  </tr>
</table>

---

<h2 style="color:#007bff;">📁 Project Structure</h2>
<pre style="background:#1e1e1e; color:#c9d1d9; padding:15px; border-radius:8px; overflow-x:auto;">
AutoMeta/
├── src/
│   ├── main/java/com/AutoMeta/
│   │   ├── Controller/
│   │   │   └── YoutubeTagsController.java
│   │   ├── Model/
│   │   │   ├── Video.java
│   │   │   └── SearchVideo.java
│   │   ├── Service/
│   │   │   ├── YoutubeService.java
│   │   │   └── ThumbnailService.java
│   │   └── AutoMetaApplication.java
│   └── resources/
│       ├── application.properties
│       └── static / templates (UI files)
├── pom.xml
├── Dockerfile
└── README.html
</pre>

---

<h2 style="color:#007bff;">⚙️ API Endpoints</h2>
<pre style="background:#f8f9fa; color:#212529; padding:15px; border-radius:8px;">
GET /api/youtube/search?query=keyword
   → Returns list of related videos and metadata

GET /api/youtube/thumbnail/{videoId}
   → Returns thumbnail URL for the video

GET /api/youtube/tags/{videoId}
   → Returns tags and related content suggestions
</pre>

---

<h2 style="color:#007bff;">🚀 Deployment</h2>
<ul style="font-size:16px;">
  <li>🛠 Built using multi-stage Docker build (Maven + Eclipse Temurin JDK 17)</li>
  <li>☁️ Hosted on <b>Render</b> under free tier with GitHub CI/CD integration</li>
  <li>🔐 Environment variable for <b>YouTube API Key</b> securely stored on Render</li>
  <li>🌍 Accessible via <code>https://autometa.onrender.com</code> (sample)</li>
</ul>

---

<h2 style="color:#007bff;">📈 Future Improvements</h2>
<ul style="font-size:16px;">
  <li>✅ Add OAuth 2.0 for secure user login with Google</li>
  <li>✅ Add UI dashboard for analytics visualization</li>
  <li>✅ Integrate caching (Redis) for faster search results</li>
  <li>✅ Add pagination and result filters (date, views, channel)</li>
</ul>

---

<h2 style="color:#007bff;">👨‍💻 Author</h2>
<div style="background:#121212; padding:20px; border-radius:8px;">
  <p style="font-size:18px; color:#fff;">Created with ❤️ by <b>Neelmani Bhardwaj</b></p>
  <p style="color:#aaa;">📍 Ottawa, Canada | 🌐 <a href="https://github.com/bhar007-neel" style="color:#0d6efd;">GitHub</a></p>
</div>

---

<div align="center" style="margin-top:40px; color:#888; font-size:14px;">
  ⭐ If you like this project, consider giving it a <b>Star</b> on GitHub! ⭐
</div>
