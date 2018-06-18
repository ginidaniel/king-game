# Interview Test

**Considerations**

- SessionKey was generated randomly based on the Local time now.
- Timeout was set as 1 second for testing reasons.
- ScoreManager could have a data source and abstract the data structures there.
- There are 2 data structures to store the scores:
    - A List that contains all the scores per level and user.
    - A Map which contains the highest scores por user per level. This structure would be quickly accessible when submiting millions of users scores.
- Concurrency was concidered when submiting new scores and updating the highest scores data structure