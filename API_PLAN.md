## Core Actions
1. Post a shift
2. View all shifts
3. Request a shift
4. Approve shift
5. Update Profile
6. Reminders/confirmations about shift

## Entities
1. User
2. Shift
3. Shift Request
4. Reminders

## Conversation Service
Manages structured conversations per user.
Conversations can follow different lines, most match the core actions.
Conversations usually walk a user through a CRUD operation.
Conversations are managed per user_id and persist over time.
This means that conversations can be continued on different devices and 
that they do not reset over time.
Responses to conversations will initially be text but will soon include buttons.

Users are initially presented with a list of opens for conversations they can start.
Users select a conversation and then go through the structured back and forth.
At any time a user can exit a conversation, abandon their progress, and return to the 
home list.

### Endpoints
- Public: getPossibleConversationsResponse
    - **Arguments:** None
    - **Return:** ConversationResponse 
    - Returns response with list of possible conversations that the service supports.
    This is presented as the home list in the app.
- Public: handleConversationResponse
    - **Arguments:** ConversationResponse, UserId
    - **Return:** List of ConversationResponse
    - Manages an arbitrary response from a user.
    Checks if response matches a global command such as 'exit'. 
    Checks if user is currently in a conversation and if so, continues structured responses.
    If structured conversation concludes, getConversationResponse is added to list of responses.
    If user is not in structured conversation, checks if response matches a 
    conversation type and starts conversation.
- Private: startConversation
    - **Arguments:** ConversationType, UserId
    - **Return:** List of ConversationResponse  
    - Initiates a specific conversation with a user and returns the first response.
    User is saved as being in this specific conversation. 
    Possible to return multiple messages

### Entities
- User
    - Has a name and id
- Conversation
    - Holds conversation history for a specific structured conversation with a single user.
    - Keeps track of current progress in structured conversation.
    - Has a ConversationType
    - Has a ConversationStage
- ConversationResponse
    - Text response from either user or service. 
    - Tagged as coming from user or service
    - May hold button and multimedia in the future.
- ConversationType
    - Enum of all possible structured conversations
- ConversationStage
    - Label of current progress in structured conversation